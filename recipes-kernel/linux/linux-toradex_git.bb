inherit kernel
require recipes-kernel/linux/linux-toradex.inc
include conf/tdx_version.conf

LINUX_VERSION ?= "3.1.10"

LOCALVERSION = "-${PR}"
PR = "${TDX_VER_ITEM}"

SRCREV = "4f04efd8590618827d15c41dabfe7e31bb0b8ac4"
SRCREV_use-head-next = "${AUTOREV}"

PV = "${LINUX_VERSION}+gitr${SRCPV}"
S = "${WORKDIR}/git"
SRCBRANCH = "tegra"
SRCBRANCH_use-head-next = "tegra-next"
SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=${SRCBRANCH}"

COMPATIBLE_MACHINE = "(apalis-t30|colibri-pxa|colibri-t20|colibri-t30)"

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
    DEFCONFIG="`echo ${MACHINE} | sed -e 's/\-/\_/g' -e 's/$/_defconfig/'`"

    cd ${S}
    export KBUILD_OUTPUT=${B}
    oe_runmake $DEFCONFIG

    #maybe change some configuration
    config_script

    #Add Toradex BSP Version as LOCALVERSION
    sed -i -e /CONFIG_LOCALVERSION/d ${B}/.config
    echo "CONFIG_LOCALVERSION=\"${LOCALVERSION}\"" >> ${B}/.config

    #Add GIT revision to the local version
    head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
    printf "%s%s" +g $head > ${S}/.scmversion

    cd - > /dev/null
}

do_uboot_mkimage_prepend () {
    cd ${B}
}

# glibc 2.24 set the oldest kernel to 3.2.0, however the downstream L4T 3.1.10
# kernel provides all needed interfaces, so override the check_oldest_kernel to
# disable the warning
python check_oldest_kernel() {
    oldest_kernel = d.getVar('OLDEST_KERNEL', True)
    kernel_version = "3.2.0"
    tclibc = d.getVar('TCLIBC', True)
    if tclibc == 'glibc':
        kernel_version = kernel_version.split('-', 1)[0]
        if oldest_kernel and kernel_version:
            if bb.utils.vercmp_string(kernel_version, oldest_kernel) < 0:
                bb.warn('%s: OLDEST_KERNEL is "%s" but the version of the kernel you are building is "%s" - therefore %s as built may not be compatible with this kernel. Either set OLDEST_KERNEL to an older version, or build a newer kernel.' %(d.getVar('PN', True), oldest_kernel, kernel_version, tclibc))
}
