HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"
PE = "1"

PV = "2019.07+git${SRCPV}"
inherit toradex-u-boot-localversion

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "e1cbe8c74e87036e649b0e34656aebabb3aa00c7"
SRCBRANCH = "toradex_2019.07"
SRCREV_use-head-next = "${AUTOREV}"
SRCBRANCH_use-head-next = "toradex_2019.07-next"

SRC_URI = "git://git.toradex.com/u-boot-toradex.git;protocol=https;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"
