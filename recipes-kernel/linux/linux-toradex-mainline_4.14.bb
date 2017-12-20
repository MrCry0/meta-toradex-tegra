SUMMARY = "Linux Kernel for Toradex Apalis Tegra based modules"
SECTION = "kernel"
LICENSE = "GPLv2"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-toradex-mainline-4.14:"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel siteinfo
include conf/tdx_version.conf

LINUX_VERSION ?= "4.14"

LOCALVERSION = "-${PR}"
PR = "${TDX_VER_INT}"

PV = "${LINUX_VERSION}"
S = "${WORKDIR}/linux-${PV}"
TK1-PATCHES = " \
    file://0001-toradex_apalis_tk1_t30-customize-defconfig.patch \
    file://0002-apalis_t30-tk1-fix-pcie-clock-and-reset-not-conformi.patch \
    file://0003-igb-integrate-tools-only-device-support.patch \
    file://0004-apalis_t30-tk1-igb-no-nvm-and-Ethernet-MAC-address-h.patch \
    file://0005-mmc-tegra-apalis-tk1-hack-to-make-sd1-functional.patch \
    file://0006-tegra_defconfig-snapd-squashfs-configuration.patch \
    file://0007-ARM-tegra-apalis-tk1-support-v1.2-hardware-revision.patch \
    file://0008-apalis-tk1-fix-pcie-reset-for-reliable-gigabit-ether.patch \
"
SRC_URI = " \
    https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-${PV}.tar.xz \
    ${TK1-PATCHES} \
"
SRC_URI[md5sum] = "bacdb9ffdcd922aa069a5e1520160e24"
SRC_URI[sha256sum] = "f81d59477e90a130857ce18dc02f4fbe5725854911db1e7ba770c7cd350f96a7"

# For CI use one could use the following instead (plus patches still of course)
LINUX_VERSION_use-head-next ?= "4.14"
SRCREV_use-head-next = "${AUTOREV}"
PV_use-head-next = "${LINUX_VERSION}+git${SRCPV}"
S_use-head-next = "${WORKDIR}/git"
SRCBRANCH_use-head-next = "linux-4.14.y"
SRC_URI_use-head-next = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;protocol=git;branch=${SRCBRANCH} \
    ${TK1-PATCHES} \
"

COMPATIBLE_MACHINE = "(apalis-tk1-mainline|apalis-t30-mainline)"
KERNEL_EXTRA_ARGS = " LOADADDR=0x80008000 "

# One possibiltiy for changes to the defconfig:
config_script () {
    echo "dummy" > /dev/null
}

do_configure_prepend () {
    cd ${S}
    export KBUILD_OUTPUT=${B}
    oe_runmake ${KERNEL_DEFCONFIG}

    #maybe change some configuration
    config_script

    #Add Toradex BSP Version as LOCALVERSION
    sed -i -e /CONFIG_LOCALVERSION/d ${B}/.config
    echo "CONFIG_LOCALVERSION=\"${LOCALVERSION}\"" >> ${B}/.config

    cd - > /dev/null
}

do_uboot_mkimage_prepend() {
    cd ${B}
}

