SUMMARY = "TegraRCM"
DESCRIPTION = "Utility used to upload payloads to a NVIDIA Tegra based device in recovery mode (RCM)."
SECTION = "bootloader"
DEPENDS = "libusb1 libcryptopp"

LICENSE = "NVIDIA-Public"
LIC_FILES_CHKSUM = "file://LICENSE;md5=395fe5affb633ad84474e42989a8e5be"

BBCLASSEXTEND = "native nativesdk"

SRC_URI = " \
    git://github.com/NVIDIA/tegrarcm.git \
    file://0001-makefile-specify-cryptopp-include-path-relative-to-s.patch \
"
SRC_URI_append_class-native = " \
    file://0001-configure.ac-link-crypotpp-as-a-static-library.patch \
    file://0001-cryptopp-use-relative-path-for-cryptopp-headers.patch \
"

SRCREV = "ec1eeac53420de6b34e814ebd28e92099b257487"
PV = "1.7+"

EXTRA_OEMAKE = 'PREFIX="${prefix}" LIBDIR="${libdir}"'

LDFLAGS_append = " -static-libstdc++"

#we want tegrarcm binary to run on a 32-bit architecture, on x86_64 this requires the 32-bit compatibility libs
EXTRA_OEMAKE_class-native = "CC='${CC}' CXX='${CXX}'"
EXTRA_OEMAKE_class-nativesdk = "CC='${CC}' CXX='${CXX}'"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

