COMPATIBLE_MACHINE_tegra124 = "(tegra124)"

PACKAGE_ARCH_tegra124 = "${MACHINE_ARCH}"

PACKAGECONFIG_tegra124 ?= "dri2 dri3 xshmfence glamor glx xwayland udev ${XORG_CRYPTO} "
