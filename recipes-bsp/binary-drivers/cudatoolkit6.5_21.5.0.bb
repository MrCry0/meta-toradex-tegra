DESCRIPTION = "NVIDIA CUDA 6.5 Toolkit for L4T Rel 21.x Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "Proprietary"

SRC_URI = "http://developer.download.nvidia.com/devzone/devcenter/mobile/jetpack_l4t/005/linux-x64/cuda-repo-l4t-r21.5-6-5-local_6.5-53_armhf.deb"

LIC_FILES_CHKSUM = "file://usr/share/doc/cuda-repo-l4t-r21.5-6-5-local/copyright;md5=f87877410d6936081b0b8ddd124ca96d"

SRC_URI[md5sum] = "93b5c6592292565c389c46a16b4cbf5d"
SRC_URI[sha256sum] = "4739c63e16b9a169f988c2b8f175c0182fa118833b49950fe72fe8c039ba1359"

DEPENDS = "dpkg-native"

INSANE_SKIP_${PN} = "ldflags"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/cuda6.5"

INSANE_SKIP_${PN}-dev = "ldflags"

FILES_${PN} = "${datadir}/lintian/* \
               ${base_prefix}/usr/local/cuda-6.5/* "

INHIBIT_PACKAGE_STRIP = "1"

INSANE_SKIP_${PN} += "already-stripped dev-so libdir"

python do_unpack () {
    locals = d.getVar('WORKDIR', True)
    s = d.getVar('S', True)
    d.setVar('WORKDIR', s)
    d.setVar('S', s)
    bb.build.exec_func('base_do_unpack', d)
    d.setVar('WORKDIR', locals)
}

do_install () {
    cp -r ${WORKDIR}/cuda6.5/usr ${D}
    dpkg -x ${WORKDIR}/cuda6.5/var/cuda-repo-6-5-local/cuda-cudart-6-5_6.5-53_armhf.deb ${D}
}
