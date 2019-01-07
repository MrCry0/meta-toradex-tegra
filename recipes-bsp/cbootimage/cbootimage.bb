SUMMARY = "cbootimage"
DESCRIPTION = "Utility to create boot images with boot configuration data (BCT) for NVIDIA Tegra SoCs."
SECTION = "bootloader"
DEPENDS = ""

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

BBCLASSEXTEND = "native nativesdk"

SRC_URI = "git://github.com/NVIDIA/cbootimage.git"
SRCREV = "a64c3f12b1b048096d90c44db74d3d110cea6fff"
PV = "1.7"

EXTRA_OEMAKE = 'PREFIX="${prefix}" LIBDIR="${libdir}"'

EXTRA_OEMAKE_class-native = "'CC=${CC}' 'RANLIB=${RANLIB}' 'AR=${AR}' 'CFLAGS=${CFLAGS} -I${S}/include' 'BUILDDIR=${S}'"

S = "${WORKDIR}/git"

inherit autotools

