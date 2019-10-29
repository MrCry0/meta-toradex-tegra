SUMMARY = "Linux Kernel for Toradex Apalis Tegra based modules"
SECTION = "kernel"
LICENSE = "GPLv2"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-toradex-mainline-4.14:"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel siteinfo

LINUX_VERSION ?= "4.14.151"

LOCALVERSION = "-${PR}"
PR = "${TDX_VER_ITEM}"

PV = "${LINUX_VERSION}"
S = "${WORKDIR}/linux-${PV}"
GENERIC_PATCHES = " \
    file://0001-apalis-t30-tk1-mainline-customize-defconfig.patch \
    file://0002-apalis_t30-tk1-fix-pcie-clock-and-reset-not-conformi.patch \
    file://0003-igb-integrate-tools-only-device-support.patch \
    file://0004-apalis_t30-tk1-igb-no-nvm-and-Ethernet-MAC-address-h.patch \
    file://0005-mmc-tegra-apalis-tk1-hack-to-make-sd1-functional.patch \
    file://0006-apalis-colibri_t30-apalis-tk1-snapd-squashfs-configu.patch \
    file://0007-ARM-tegra-apalis-tk1-Support-v1.2-hardware-revision.patch \
    file://0008-apalis-tk1-fix-pcie-reset-for-reliable-gigabit-ether.patch \
    file://0009-drm-tegra-gem-Reshuffle-declarations.patch \
    file://0010-drm-tegra-gem-Make-__tegra_gem_mmap-available-more-w.patch \
    file://0011-drm-tegra-fb-Implement-fb_mmap-callback.patch \
    file://0012-apalis-tk1-support-for-k20-mfd.patch \
    file://0013-usb-chipidea-tegra-Use-aligned-DMA-on-Tegra30.patch \
    file://0014-usb-chipidea-tegra-Use-aligned-DMA-on-Tegra114-124.patch \
    file://0015-Revert-mmc-core-fix-error-path-in-mmc_host_alloc.patch \
    file://0016-Revert-mmc-core-simplify-ida-handling.patch \
    file://0017-mmc-read-mmc-alias-from-device-tree.patch \
    file://0018-apalis-t30-mainline-force-fixed-ids-for-sdmmc-contro.patch \
    file://0019-ARM-tegra-apalis-tk1-Fix-high-speed-UART-compatible.patch \
    file://0020-apalis-t30-tk1-mainline-igb-avoid-nvm-checksum-failu.patch \
    file://0021-apalis_t30-fix-can2.patch \
    file://0022-mfd-as3722-disable-auto-power-on-when-AC-OK.patch \
    file://0023-apalis-tk1-mfd-k20-update-api-header-for-fw-version-.patch \
    file://0024-apalis_t30-enable-broken-hpi-on-emmc.patch \
    file://0026-apalis_t30-fix-mmc1-cmd-pull-up.patch \
    file://0027-apalis_t30-pull-up-sd-card-detect-pins.patch \
    file://0028-Revert-mmc-tegra-Disable-UHS-I-modes-for-Tegra124.patch \
    file://0029-mmc-tegra-fix-eMMC-DDR-mode.patch \
    file://0030-apalis-tk1-enable-ddr52-mode-on-emmc.patch \
    file://0031-apalis-tk1-force-fixed-ids-for-sdmmc-controllers.patch \
    file://0032-apalis-tk1-fix-pcie-reset-for-reliable-gigabit-ether.patch \
    file://0033-apalis-tk1-mfd-k20-supporte-for-fw-version-1.3.patch \
    file://0034-apalis-tk1-mfd-k20-update-supported-fw-version-to-1..patch \
"
MACHINE_PATCHES = " \
"
MACHINE_PATCHES_apalis-t30-mainline = " \
    file://0033-apalis-t30-mainline-apply-pcie-fix-hacks.patch \
"
SRC_URI = " \
    https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-${PV}.tar.xz \
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
"
SRC_URI[md5sum] = "5a1ac8dcce45d3b66fd40ac0d0feaca9"
SRC_URI[sha256sum] = "ff519c428ee9bbb513a84db5ec32a7e3705cd8c23a57104b25b944cb79583fae"

# For CI use one could use the following instead (plus patches still of course)
LINUX_VERSION_use-head-next ?= "4.14"
SRCREV_use-head-next = "${AUTOREV}"
PV_use-head-next = "${LINUX_VERSION}+git${SRCPV}"
S_use-head-next = "${WORKDIR}/git"
SRCBRANCH_use-head-next = "linux-4.14.y"
SRC_URI_use-head-next = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;protocol=git;branch=${SRCBRANCH} \
    ${GENERIC_PATCHES} \
    ${MACHINE_PATCHES} \
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

# defaults
TDX_VER_ITEM ??= "0"
