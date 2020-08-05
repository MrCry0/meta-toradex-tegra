require u-boot-toradex-common-tk1.inc
require recipes-bsp/u-boot/u-boot.inc

B = "${WORKDIR}/build"

DEPENDS += "bc-native dtc-native"
DEPENDS_append_apalis-tk1 = " cbootimage-native"

PROVIDES += "u-boot"

COMPATIBLE_MACHINE = "(apalis-tk1)"

SRC_URI_append_apalis-tk1 = " \
    file://apalis-tk1.img.cfg \
    file://fw_env.config \
    file://PM375_Hynix_2GB_H5TC4G63AFR_RDA_924MHz.bct \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_deploy_append_apalis-tk1() {
    cd ${DEPLOYDIR}
    cp ${WORKDIR}/PM375_Hynix_2GB_H5TC4G63AFR_RDA_924MHz.bct .
    cbootimage -s tegra124 ${WORKDIR}/apalis-tk1.img.cfg apalis-tk1.img
    rm PM375_Hynix_2GB_H5TC4G63AFR_RDA_924MHz.bct
}

