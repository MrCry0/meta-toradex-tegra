require recipes-bsp/u-boot/u-boot.inc
require recipes-bsp/u-boot/u-boot-toradex-env.inc
inherit tegra-u-boot-localversion

PROVIDES = "u-boot virtual/bootloader"
DEPENDS += "dtc-native"
DEPENDS_append_apalis-tk1 = " cbootimage-native"
DEPENDS_append_apalis-tk1-mainline = " cbootimage-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

COMPATIBLE_MACHINE = "(apalis-tk1)"

FILESPATHPKG =. "git:"
S = "${WORKDIR}/git"
# This revision is based on upstream "v2016.11"
SRCREV = "83a53c1c0c6fd813bd655b4f88fd07bf798e11d7"
SRCBRANCH = "2016.11-toradex"
SRCREV_use-head-next = "${AUTOREV}"
SRCBRANCH_use-head-next = "2016.11-toradex-next"
SRC_URI = " \
    git://git.toradex.com/u-boot-toradex.git;protocol=git;branch=${SRCBRANCH} \
    file://0001-libfdt-move-headers-to-linux-libfdt.h-and-linux-libf.patch \
"

SRC_URI_append_apalis-tk1 = " \
    file://apalis-tk1.img.cfg \
    file://PM375_Hynix_2GB_H5TC4G63AFR_RDA_924MHz.bct \
"
SRC_URI_append_apalis-tk1-mainline = " \
    file://apalis-tk1.img.cfg \
    file://PM375_Hynix_2GB_H5TC4G63AFR_RDA_924MHz.bct \
"

PV = "2016.11+git${SRCPV}"
PR = "${TDX_VER_ITEM}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_deploy_append_apalis-tk1() {
    cd ${DEPLOYDIR}
    cp ${WORKDIR}/PM375_Hynix_2GB_H5TC4G63AFR_RDA_924MHz.bct .
    cbootimage -s tegra124 ${WORKDIR}/apalis-tk1.img.cfg apalis-tk1.img
    rm PM375_Hynix_2GB_H5TC4G63AFR_RDA_924MHz.bct
}
do_deploy_append_apalis-tk1-mainline() {
    cd ${DEPLOYDIR}
    cp ${WORKDIR}/PM375_Hynix_2GB_H5TC4G63AFR_RDA_924MHz.bct .
    cbootimage -s tegra124 ${WORKDIR}/apalis-tk1.img.cfg apalis-tk1.img
    rm PM375_Hynix_2GB_H5TC4G63AFR_RDA_924MHz.bct
}

# defaults
TDX_VER_ITEM ??= "0"
