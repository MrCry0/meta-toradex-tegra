PACKAGECONFIG_append_tegra124 = " dri3 egl gles gallium gbm "
EXTRA_OECONF_append_tegra124 = " --enable-texture-float --without-dri-drivers --enable-glx --enable-osmesa --enable-debug"
DRIDRIVERS_tegra124 = " "
GALLIUMDRIVERS_tegra124 = "nouveau,tegra"

PACKAGE_ARCH_tegra124 = "${MACHINE_ARCH}"
