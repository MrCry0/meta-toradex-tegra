DESCRIPTION = "NVIDIA Linux Driver Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=fbc093901857fcd118f065f900982c24"

SRC_URI = " \
    https://developer.download.nvidia.com/embedded/L4T/r21_Release_v7.0/r21.7.0-sources.tbz2 \
    file://0001-fix-gstomx-compilation-issues.patch \
    file://0001-configure-don-t-use-gst-photography-even-if-it-is-in.patch \
"

SRC_URI[md5sum] = "620d0979ffbbc8cbe8cb36faacee386b"
SRC_URI[sha256sum] = "4523ce03e18d2507a180a0b67e3ef136d4b73837f48063146c471fe575282440"

DEPENDS += " libgstnvegl libffi glib-2.0 gstreamer gst-plugins-base libpcre libxml2 zlib \
             virtual/egl virtual/mesa virtual/libgles2 gdbm drm tar-native"

S = "${WORKDIR}/gstomx_src/gst-openmax"

inherit autotools pkgconfig

EXTRA_OECONF = " --enable-tegra --enable-eglimage --enable-experimental --disable-static "

CXXFLAGS += " -DTEGRA_ARCH_124 -I${S}/omx/openmax -I${WORKDIR}/gstomx_src/nv_headers -Wl,--no-undefined "
CFLAGS += " -DTEGRA_ARCH_124 -I${S}/omx/openmax -I${WORKDIR}/gstomx_src/nv_headers -Wl,--no-undefined "

nvidia_unpack() {
    cd "${WORKDIR}"
    # NVIDIA now packages packages in one big package!
    tar xjf gstomx_src.tbz2
}

do_unpack[postfuncs] += "nvidia_unpack"

do_configure_prepend() {
	export LIBS="-lEGL -lGLESv2 -lX11 -ldl -lgstreamer-0.10 "
}

FILES_${PN}-dbg = " \
    /usr/src/debug/* \
    /usr/lib/.debug/* \
"
FILES_${PN} = " \
    /usr/lib/gstreamer-0.10/libgstomx.so \
    /usr/lib/gstreamer-0.10/libgstomx.la \
"
