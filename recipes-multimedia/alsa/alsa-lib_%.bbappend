FILESEXTRAPATHS_prepend_tegra124 := "${THISDIR}/${PN}:"

PACKAGE_ARCH_tegra124 = "${MACHINE_ARCH}"

SRC_URI_append_tegra124 = " file://0001-alsa-conf-select-default-soundcard.patch"
