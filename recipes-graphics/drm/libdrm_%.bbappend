PACKAGES += " ${PN}-tegra"
FILES_${PN}-tegra = "${libdir}/libdrm_tegra.so.*"

EXTRA_OECONF_append += " --enable-tegra-experimental-api"
