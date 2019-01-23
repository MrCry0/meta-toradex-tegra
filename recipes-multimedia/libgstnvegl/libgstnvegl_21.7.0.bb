DESCRIPTION = "NVIDIA Linux Driver Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d"

SRC_URI = " \
    https://developer.download.nvidia.com/embedded/L4T/r21_Release_v7.0/r21.7.0-sources.tbz2 \
    file://0001-rename-gstegl-to-gstnvegl.patch \
    file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
    file://0001-pkg-config-files-fix-qa-error.patch \
"

SRC_URI[md5sum] = "620d0979ffbbc8cbe8cb36faacee386b"
SRC_URI[sha256sum] = "4523ce03e18d2507a180a0b67e3ef136d4b73837f48063146c471fe575282440"

S = "${WORKDIR}/gstegl_src/gst-egl"

inherit autotools pkgconfig
# gobject-introspection

EXTRA_OECONF = "--disable-nls --disable-static-plugins --enable-introspection=no"

DEPENDS += " libffi glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base libpcre libxml2 zlib \
             virtual/egl virtual/mesa virtual/libgles2 wayland gdbm drm tar-native"

nvidia_unpack() {
    cd "${WORKDIR}"
    # NVIDIA now packages packages in one big package!
    tar xjf gstegl_src.tbz2
}

do_unpack[postfuncs] += "nvidia_unpack"

FILES_${PN}-dbg = " \
    /usr/src/debug/* \
    /usr/lib/gstreamer-1.0/.debug/* \
    /usr/lib/.debug/* \
"

FILES_${PN} = " \
    /usr/lib/gstreamer-1.0/libgstnveglglessink.so \
    /usr/lib/gstreamer-1.0/libgstnveglglessink.la \
    /usr/lib/libgstnvegl-1.0.so \
    /usr/lib/libgstnvegl-1.0.so.0 \
    /usr/lib/libgstnvegl-1.0.so.0.203.0 \
"
