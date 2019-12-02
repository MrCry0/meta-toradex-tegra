SUMMARY = "Linux Kernel for Toradex Tegra124 based modules"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel siteinfo toradex-kernel-localversion

LINUX_VERSION ?= "3.10.40"

SRCREV = "ad44961f8b3cf5ce342d234c7eca05d89369c26b"
SRCREV_use-head-next = "${AUTOREV}"

PV = "${LINUX_VERSION}+gitr${SRCPV}"
S = "${WORKDIR}/git"
SRCBRANCH = "toradex_tk1_l4t_r21.7"
SRCBRANCH_use-head-next = "toradex_tk1_l4t_r21.7-next"
SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=${SRCBRANCH}"

COMPATIBLE_MACHINE = "apalis-tk1"

KERNEL_MODULE_AUTOLOAD += "xhci-hcd"

# since thud modules no longer load without this
# [    9.302483] apalis_tk1_k20: Unknown symbol _GLOBAL_OFFSET_TABLE_ (err 0)
KERNEL_EXTRA_ARGS_append = "CFLAGS_MODULE=-fno-pic"

# One possibiltiy for changes to the defconfig:
config_script () {
#    #example change to the .config
#    #sets CONFIG_TEGRA_CAMERA unconditionally to 'y'
#    sed -i -e /CONFIG_TEGRA_CAMERA/d ${B}/.config
#    echo "CONFIG_TEGRA_CAMERA=y" >> ${B}/.config
    echo "dummy" > /dev/null
}

do_configure_prepend () {
    #use the defconfig provided in the kernel source tree
    #assume its called ${MACHINE}_defconfig, but with '_' instead of '-'
    DEFCONFIG="`echo ${MACHINE} | sed -e 's/$/_defconfig/'`"

    cd ${S}
    export KBUILD_OUTPUT=${B}
    oe_runmake $DEFCONFIG

    #maybe change some configuration
    config_script

    cd - > /dev/null
}

do_uboot_mkimage_prepend() {
    cd ${B}
}
