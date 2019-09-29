#####
# tegra tk1

PACKAGE_ARCH_tegra124 = "${MACHINE_ARCH}"

PROVIDES_remove_tegra124   = "virtual/egl virtual/libgl virtual/libgles1 virtual/libgles2"

do_install_append_tegra124 () {
    rm ${D}${libdir}/libEGL.so*
    rm ${D}${libdir}/libGL.so*
    rm ${D}${libdir}/libGLESv1*.so*
    rm ${D}${libdir}/libGLESv2.so*
}

#####
## Tegra TK1 mainline kernel

PACKAGECONFIG_append_tegra124m = " dri3 egl gles gallium gbm "
EXTRA_OECONF_append_tegra124m = " --enable-texture-float --without-dri-drivers --enable-glx --enable-osmesa --enable-debug"
DRIDRIVERS_tegra124m = " "
GALLIUMDRIVERS_tegra124m = "nouveau,tegra"

PACKAGE_ARCH_tegra124m = "${MACHINE_ARCH}"
PE_tegra124m = "99"
