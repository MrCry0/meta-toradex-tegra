FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

#####
# tegra tk1

DEPENDS_REMOVE = "linux-driver-package"
DEPENDS_REMOVE_tegra124 = ""
DEPENDS_remove = "${DEPENDS_REMOVE}"

SRC_URI_append_tegra124 = " file://0001-pkg-config-files-add-tegra-paths.patch"

PACKAGE_ARCH_tegra124 = "${MACHINE_ARCH}"

# until meta-jetson-tk1 adds it through its bbappend:
DEPENDS_append_tegra124= " linux-driver-package "

#####
## Tegra TK1 mainline kernel

PACKAGECONFIG_append_tegra124m = " dri3 egl gles gallium gbm "
EXTRA_OECONF_append_tegra124m = " --enable-texture-float --without-dri-drivers --enable-glx --enable-osmesa --enable-debug"
DRIDRIVERS_tegra124m = " "
GALLIUMDRIVERS_tegra124m = "nouveau,tegra"

PACKAGE_ARCH_tegra124m = "${MACHINE_ARCH}"
PE_tegra124m = "99"
