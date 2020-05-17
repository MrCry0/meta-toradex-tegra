#####
## Tegra TK1 mainline kernel

PACKAGECONFIG_append_tegra124m = " dri3 egl gles gallium gbm "
EXTRA_OECONF_append_tegra124m = " --enable-texture-float --without-dri-drivers --enable-glx --enable-osmesa --enable-debug"
DRIDRIVERS_tegra124m = " "
GALLIUMDRIVERS_tegra124m = "nouveau,tegra"

PACKAGE_ARCH_tegra124m = "${MACHINE_ARCH}"
PE_tegra124m = "99"
