SUMMARY = "Linux Kernel for Toradex Tegra124 based modules"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel siteinfo
require recipes-kernel/linux/linux-dtb.inc

LINUX_VERSION ?= "3.10.40"

LOCALVERSION = "-${PR}"
SRCREV = "eeb6061f4b93cdd2c946e354ff69224163636282"
PR = "V2.6b2"

PV = "${LINUX_VERSION}+gitr${SRCREV}"
S = "${WORKDIR}/git"
SRCBRANCH = "toradex_tk1_l4t_r21.5"
SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=${SRCBRANCH}"

COMPATIBLE_MACHINE = "apalis-tk1"

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

    #Add Toradex BSP Version as LOCALVERSION
    sed -i -e /CONFIG_LOCALVERSION/d ${B}/.config
    echo "CONFIG_LOCALVERSION=\"${LOCALVERSION}\"" >> ${B}/.config

    #Add GIT revision to the local version
    head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
    printf "%s%s" +g $head > ${S}/.scmversion
}

kernel_do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
    export CC="`echo "${KERNEL_CC}" | sed 's/-mfloat-abi=hard//g'`"
    oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} LD="${KERNEL_LD}"
    if test "${KERNEL_IMAGETYPE_FOR_MAKE}.gz" = "${KERNEL_IMAGETYPE}"; then
        gzip -9c < "${KERNEL_IMAGETYPE_FOR_MAKE}" > "${KERNEL_OUTPUT}"
    fi
}

do_compile_kernelmodules() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
    export CC="`echo "${KERNEL_CC}" | sed 's/-mfloat-abi=hard//g'`"
    if (grep -q -i -e '^CONFIG_MODULES=y$' .config); then
        oe_runmake ${PARALLEL_MAKE} modules LD="${KERNEL_LD}"
    else
        bbnote "no modules to compile"
    fi
}
