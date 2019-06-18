FILESEXTRAPATHS_prepend := "${THISDIR}/linux-driver-package:"

SRC_URI_append_apalis-tk1 = " \
    file://xorg.conf \
    file://nvfb.service \
    file://nv.service \
"
inherit systemd

do_install_append_apalis-tk1 () {
    rm ${D}/usr/lib/libGL.so
    ln -sf libGL.so.1 ${D}/usr/lib/arm-linux-gnueabihf/tegra/libGL.so

    cp ${WORKDIR}/xorg.conf ${D}/etc/X11/
    install -d ${D}${systemd_unitdir}/system/
    install -m 0755 ${WORKDIR}/nvfb.service ${D}${systemd_unitdir}/system
    install -m 0755 ${WORKDIR}/nv.service ${D}${systemd_unitdir}/system
    install -m 0755 ${WORKDIR}/nv ${D}${bindir}
    install -m 0755 ${WORKDIR}/nvfb ${D}${bindir}
}

# deploy additional binaries from the nv_gst_apps tarball
PACKAGES_prepend = "${PN}-gstnvcamera ${PN}-gstnvvidconv-1.0 ${PN}-nvgstjpeg-1.0 ${PN}-nvgstapps "
RRECOMMENDS_${PN}_append = " ${PN}-gstnvcamera ${PN}-gstnvvidconv-1.0 ${PN}-nvgstjpeg-1.0 ${PN}-nvgstapps"

RDEPENDS_${PN}-gstnvcamera = "libgstvideo-1.0"
RDEPENDS_${PN}-gstnvvidconv-1.0 = "libgstvideo-1.0"
RDEPENDS_${PN}-nvgstjpeg-1.0 = "libgstvideo-1.0"
RDEPENDS_${PN}-nvgstapps = "libgstpbutils-1.0"

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

FILES_${PN}-boot += " \
    ${bindir}/nv \
    ${systemd_unitdir}/system/nv.service \
"

FILES_${PN}-firstboot += "\
    ${bindir}/nvfb \
    ${systemd_unitdir}/system/nvfb.service \
"

#no gnu_hash in NVIDIA binaries, skip QA dev-so for this package
#we have symlinks ending in .so, skip QA ldflags for this package
#inhibit warnings about files being stripped
INSANE_SKIP_${PN} = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-gstnvcamera = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-gstnvvidconv-1.0 = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-nvgstjpeg-1.0 = "build-deps dev-so ldflags already-stripped textrel"
INSANE_SKIP_${PN}-nvgstapps = "build-deps dev-so ldflags already-stripped textrel"

do_install_append () {
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

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "nv.service nvfb.service"
