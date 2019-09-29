DESCRIPTION = "NVIDIA Linux Driver Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "Proprietary"

SRC_URI = " \
    http://developer.download.nvidia.com/embedded/L4T/r21_Release_v7.0/Tegra124_Linux_R21.7.0_armhf.tbz2 \
    file://xorg.conf.add \
    file://nv \
    file://nvfb \
    file://tegra_xusb_firmware \
    file://xorg.conf \
    file://nvfb.service \
    file://nv.service \
"

LIC_FILES_CHKSUM = "file://nv_tegra/LICENSE;md5=60ad17cc726658e8cf73578bea47b85f"

SRC_URI[md5sum] = "2139c0c7ecff94da68aef8a6ad0df20b"
SRC_URI[sha256sum] = "676add1e8e6b2fcf76d97f22f38c9d0cbbe8a92342039a85c8a4c87e8c1ce824"

PR = "r7"

inherit ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', 'update-rc.d', d)}

INITSCRIPT_PACKAGES = "${PN}-boot ${PN}-firstboot"

INITSCRIPT_NAME_${PN}-boot = "nv"
INITSCRIPT_PARAMS_${PN}-boot = "start 41 S . "

INITSCRIPT_NAME_${PN}-firstboot = "nvfb"
INITSCRIPT_PARAMS_${PN}-firstboot = "start 40 S . "

DEPENDS = "virtual/libx11 alsa-lib libxext"

INSANE_SKIP_${PN} = "ldflags"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/Linux_for_Tegra"

PACKAGES =+ "${PN}-firmware ${PN}-boot ${PN}-firstboot"

INSANE_SKIP_${PN}-dev = "ldflags"

FILES_${PN} =  "${bindir}/* ${libdir}/* ${sysconfdir}/* ${sysconfdir}/*/*"
RRECOMMENDS_${PN} = "xserver-xorg-module-libwfb"
RDEPENDS_${PN} = "xserver-xorg bash"

FILES_${PN}-firmware = "${base_libdir}/firmware/* ${base_libdir}/firmware/tegra12x/* "

INHIBIT_PACKAGE_STRIP = "1"
#INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN} += "dev-so"

do_patch () {
    mkdir -p ${WORKDIR}/l4tdrv
    tar xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/config.tbz2 -C ${WORKDIR}/l4tdrv
}

do_install () {
    tar xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/nvidia_drivers.tbz2 -C ${D}
    mv ${D}/lib/firmware/tegra12x ${D}/lib/firmware/gk20a
    cp -r ${WORKDIR}/tegra_xusb_firmware ${D}/lib/firmware/
    ln -sf ./libcuda.so.1.1 ${D}/usr/lib/arm-linux-gnueabihf/tegra/libcuda.so
    ln -sf ./arm-linux-gnueabihf/tegra/libcuda.so ${D}/usr/lib/libcuda.so
    ln -sf ./arm-linux-gnueabihf/tegra/libGL.so.1 ${D}/usr/lib/libGL.so
    cp ${WORKDIR}/l4tdrv/etc/asound* ${D}/etc/
    cp -r ${WORKDIR}/l4tdrv/etc/udev ${D}/etc/
    mkdir ${D}/etc/X11/ 
    cp ${WORKDIR}/l4tdrv/etc/X11/xorg.conf* ${D}/etc/X11/
    cat ${WORKDIR}/l4tdrv/etc/X11/xorg.conf.jetson-tk1 ${WORKDIR}/xorg.conf.add > ${D}/etc/X11/xorg.conf.jetson-tk1
    
    # install init scripts
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/nv ${D}${sysconfdir}/init.d/nv
    install -m 0755 ${WORKDIR}/nvfb ${D}${sysconfdir}/init.d/nvfb
    install -d ${D}${sysconfdir}/nv
    touch ${D}${sysconfdir}/nv/nvfirstboot

    rm ${D}/usr/lib/libGL.so
    ln -sf libGL.so.1 ${D}/usr/lib/arm-linux-gnueabihf/tegra/libGL.so

    cp ${WORKDIR}/xorg.conf ${D}/etc/X11/
    install -d ${D}${systemd_unitdir}/system/
    install -m 0755 ${WORKDIR}/nvfb.service ${D}${systemd_unitdir}/system
    install -m 0755 ${WORKDIR}/nv.service ${D}${systemd_unitdir}/system
    install -m 0755 ${WORKDIR}/nv ${D}${bindir}
    install -m 0755 ${WORKDIR}/nvfb ${D}${bindir}

    NV_SAMPLE=${WORKDIR}/Linux_for_Tegra/nv_tegra/nv_sample_apps
    tar xjf ${NV_SAMPLE}/nvgstapps.tbz2 -C ${NV_SAMPLE}
    install -d ${D}${bindir} ${D}${libdir}/gstreamer-1.0 ${D}${docdir}
    install -m 0755 ${NV_SAMPLE}/usr/bin/nvgstcapture-1.0 ${D}${bindir}
    install -m 0755 ${NV_SAMPLE}/usr/bin/nvgstplayer-1.0 ${D}${bindir}
    install -m 0755 ${NV_SAMPLE}/nvgstcapture-1.0_README.txt ${D}${docdir}
    install -m 0755 ${NV_SAMPLE}/nvgstplayer-1.0_README.txt ${D}${docdir}

    install -m 0755 ${NV_SAMPLE}/usr/lib/arm-linux-gnueabihf/gstreamer-1.0/libgstnvcamera.so ${D}${libdir}/gstreamer-1.0
    install -m 0755 ${NV_SAMPLE}/usr/lib/arm-linux-gnueabihf/gstreamer-1.0/libgstnvvidconv.so ${D}${libdir}/gstreamer-1.0
    install -m 0755 ${NV_SAMPLE}/usr/lib/arm-linux-gnueabihf/gstreamer-1.0/libnvgstjpeg.so ${D}${libdir}/gstreamer-1.0
}

do_populate_sysroot () {
    tar xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/nvidia_drivers.tbz2 -C ${WORKDIR}/sysroot-destdir/
    rm ${WORKDIR}/sysroot-destdir/usr/lib/xorg/modules/extensions/libglx.so
    mkdir ${WORKDIR}/sysroot-destdir/sysroot-providers
    touch ${WORKDIR}/sysroot-destdir/sysroot-providers/${PN}
}

# Function to add the relevant ABI dependency to drivers, which should be called# from a PACKAGEFUNC.
python add_xorg_abi_depends() {
    mlprefix = d.getVar('MLPREFIX', True) or ''
    abi = "%sxorg-abi-%s-%s" % (mlprefix, "video", "19")

    pn = d.getVar("PN", True)
    d.appendVar('RDEPENDS_' + pn, ' ' + abi)
}
PACKAGEFUNCS =+ "add_xorg_abi_depends"

FILES_${PN}-boot = " \
    ${bindir}/nv \
    ${sysconfdir}/init.d/nv \
    ${systemd_unitdir}/system/nv.service \
"

FILES_${PN}-firstboot = "\
    ${bindir}/nvfb \
    ${sysconfdir}/init.d/nvfb \
    ${sysconfdir}/nv/nvfirstboot \
    ${systemd_unitdir}/system/nvfb.service \
"

# deploy additional binaries from the nv_gst_apps tarball
PACKAGES_prepend = "${PN}-gstnvcamera ${PN}-gstnvvidconv-1.0 ${PN}-nvgstjpeg-1.0 ${PN}-nvgstapps "
RRECOMMENDS_${PN}_append = " ${PN}-gstnvcamera ${PN}-gstnvvidconv-1.0 ${PN}-nvgstjpeg-1.0 ${PN}-nvgstapps"

RDEPENDS_${PN}-gstnvcamera = "glib-2.0 gstreamer1.0 libgstvideo-1.0"
RDEPENDS_${PN}-gstnvvidconv-1.0 = "glib-2.0 gstreamer1.0 libgstvideo-1.0"
RDEPENDS_${PN}-nvgstjpeg-1.0 = "glib-2.0 gstreamer1.0 libgstvideo-1.0"
RDEPENDS_${PN}-nvgstapps = "glib-2.0 gstreamer1.0 libgstpbutils-1.0 libgstvideo-1.0"

FILES_${PN}-gstnvcamera = " \
    ${libdir}/gstreamer-1.0/libgstnvcamera.so \
"

FILES_${PN}-gstnvvidconv-1.0 = " \
    ${libdir}/gstreamer-1.0/libgstnvvidconv.so \
"

FILES_${PN}-nvgstjpeg-1.0 = " \
    ${libdir}/gstreamer-1.0/libnvgstjpeg.so \
"

FILES_${PN}-nvgstapps = " \
    ${bindir}/nvgstcapture-1.0 \
    ${bindir}/nvgstplayer-1.0 \
    ${docdir}/nvgst*README.txt \
"

#no gnu_hash in NVIDIA binaries, skip QA dev-so for this package
#we have symlinks ending in .so, skip QA ldflags for this package
#inhibit warnings about files being stripped
INSANE_SKIP_${PN} = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-gstnvcamera = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-gstnvvidconv-1.0 = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-nvgstjpeg-1.0 = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-nvgstapps = "build-deps dev-so ldflags already-stripped textrel"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "nv.service nvfb.service"
