DESCRIPTION = "NVIDIA OpenCV4Tegra for L4T 21.x Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "Proprietary"

SRC_URI = "http://developer.download.nvidia.com/devzone/devcenter/mobile/jetpack_l4t/003/linux-x64/libopencv4tegra-repo_2.4.13_armhf_l4t-r21.deb"

LIC_FILES_CHKSUM = "file://usr/share/doc/libopencv4tegra-repo/copyright;md5=99d8c0c1313afdf990f6407c07a88407"

SRC_URI[md5sum] = "0db305a4d24a9211606bb1fb8ec2d480"
SRC_URI[sha256sum] = "9bfa9d5df0c3218f5b4000c8d9d50d136cd8e6ad82384b7781719deb7093ae21"

DEPENDS = "dpkg-native tiff zlib jpeg glib-2.0 python gtk+ libpng12 jasper libav cudatoolkit6.5"

INSANE_SKIP_${PN} = "ldflags"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/opencv4tegra"

INSANE_SKIP_${PN}-dev = "ldflags"

FILES_${PN} = "${bindir}/* \
               ${datadir}/OpenCV/* \
               ${datadir}/lintian/* \
               ${libdir}/*"

INHIBIT_PACKAGE_STRIP = "1"

INSANE_SKIP_${PN} += "already-stripped dev-so"

python do_unpack () {
    locals = d.getVar('WORKDIR', True)
    s = d.getVar('S', True)
    d.setVar('WORKDIR', s)
    d.setVar('S', s)
    bb.build.exec_func('base_do_unpack', d)
    d.setVar('WORKDIR', locals)
}

do_install () {
    cp -r ${WORKDIR}/opencv4tegra/usr ${D}
    dpkg -x ${WORKDIR}/opencv4tegra/var/libopencv4tegra-repo/libopencv4tegra_2.4.13_armhf.deb ${D}
    dpkg -x ${WORKDIR}/opencv4tegra/var/libopencv4tegra-repo/libopencv4tegra-dev_2.4.13_armhf.deb ${D}
    dpkg -x ${WORKDIR}/opencv4tegra/var/libopencv4tegra-repo/libopencv4tegra-python_2.4.13_armhf.deb ${D}
}
