PACKAGES += " ${PN}-tegra"
FILES_${PN}-tegra = "${libdir}/libdrm_tegra.so.*"

PACKAGECONFIG_append_tegra124m = " tegra"
