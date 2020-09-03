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
SRCREV = "f867d6dc951417bbec3e60d21ef68b6e69894013"
SRCBRANCH = "toradex_2020.07"
SRCREV_use-head-next = "${AUTOREV}"

SRC_URI = "git://git.toradex.com/u-boot-toradex.git;protocol=https;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

UBOOT_INITIAL_ENV = "u-boot-initial-env"
