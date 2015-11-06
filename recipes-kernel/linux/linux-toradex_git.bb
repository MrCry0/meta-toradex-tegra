inherit kernel
require recipes-kernel/linux/linux-toradex.inc

LINUX_VERSION ?= "3.1.10"

LOCALVERSION = "-${PR}"
SRCREV_apalis-t30 = "d8eb1dde264c7b55d25209de26a08471e2f566f6"
PR_apalis-t30 = "V2.5b2"
SRCREV_colibri-pxa = "d8eb1dde264c7b55d25209de26a08471e2f566f6"
PR_colibri-pxa = "V2.5b2"
SRCREV_colibri-t20 = "d8eb1dde264c7b55d25209de26a08471e2f566f6"
PR_colibri-t20 = "V2.5b2"
SRCREV_colibri-t30 = "d8eb1dde264c7b55d25209de26a08471e2f566f6"
PR_colibri-t30 = "V2.5b2"

PV = "${LINUX_VERSION}+gitr${SRCREV}"
S = "${WORKDIR}/git"
SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=tegra"


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
