FILESEXTRAPATHS_prepend_tegra124m := "${THISDIR}/${PN}:"

PACKAGE_ARCH_tegra124m = "${MACHINE_ARCH}"

SRC_URI_append_tegra124m = " file://0001-alsa-conf-select-default-soundcard.patch"
