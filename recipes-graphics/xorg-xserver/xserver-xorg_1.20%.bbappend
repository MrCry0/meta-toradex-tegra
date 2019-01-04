# always latest and greatest.
PE_tegra124m = "99"

PACKAGE_ARCH_tegra124m = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE_tegra124m = "(apalis-tk1-mainline)"

PACKAGECONFIG_tegra124m ?= "dri2 dri3 xshmfence glamor glx xwayland udev ${XORG_CRYPTO} "
