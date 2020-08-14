HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"
PE = "1"

PV = "2020.07+git${SRCPV}"
inherit toradex-u-boot-localversion

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "c650daccd29536b63d7e1b7d3a2b6d5363c49cfd"
SRCBRANCH = "toradex_2020.07"
SRCREV_use-head-next = "${AUTOREV}"
SRCBRANCH_use-head-next = "toradex_2019.07-next"

SRC_URI = "git://git.toradex.com/u-boot-toradex.git;protocol=https;branch=${SRCBRANCH}"
SRC_URI += "file://0001-u-boot-tk1-make-it-build-with-python3-only-installat.patch"

S = "${WORKDIR}/git"

UBOOT_INITIAL_ENV = "u-boot-initial-env"
