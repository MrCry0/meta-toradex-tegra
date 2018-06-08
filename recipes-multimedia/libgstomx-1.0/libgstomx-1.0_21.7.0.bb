DESCRIPTION = "NVIDIA Linux Driver Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = " \
    http://developer.download.nvidia.com/embedded/L4T/r21_Release_v5.0/source/gstomx1_src.tbz2 \
    file://r21.6.0-sources-gstomx1_src.patch \
    file://r21.7.0-sources-gstomx1_src.patch \
    file://gstomx-1.0.patch \
"

SRC_URI[md5sum] = "d5714a9533b210b30ef0e97e28ebc77d"
SRC_URI[sha256sum] = "b1c62a41467f2ff6661a3ba7d0e30e85e7ed126005f67b39ff6ebf2d704fd222"

DEPENDS += " libgstnvegl libffi glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base libpcre libxml2 zlib "
DEPENDS += " virtual/egl virtual/mesa virtual/libgles2 wayland gdbm drm "

S = "${WORKDIR}/gstomx1_src/gst-omx1"

inherit autotools pkgconfig

EXTRA_OECONF = " --with-omx-target=tegra --disable-static-plugins"

CXXFLAGS += " -I${S}/omx/openmax "
CFLAGS += " -I${S}/omx/openmax "

do_configure_prepend() {
    export NOCONFIGURE="true"
    export GST_EGL_LIBS="-lgstnvegl-1.0 -lEGL -lX11 -lgstreamer-1.0 -lgobject-2.0 -lglib-2.0"
    export GST_EGL_CFLAGS="-I${STAGING_INCDIR}/libdrm "
#   export GST_EGL_CFLAGS="-pthread -I${STAGING_INCDIR}/gstreamer-1.0 -I${STAGING_INCDIR}/libdrm -I${STAGING_INCDIR}/glib-2.0 -I${STAGING_LIBDIR}/glib-2.0/include"
}

FILES_${PN}-dbg = " \
    /usr/src/debug/* \
    /usr/lib/gstreamer-1.0/.debug/* \
    /usr/lib/.debug/* \
"

FILES_${PN} = " \
    /usr/lib/gstreamer-1.0/libgstomx.la \
    /usr/lib/gstreamer-1.0/libgstomx.so \
"
