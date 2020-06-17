DESCRIPTION = "Toradex Apalis TK1 K20 MCU Firmware"
HOMEPAGE = "https://developer.toradex.com/"
LICENSE = "CLOSED"

SRC_URI = " \
    file://apalis-tk1-k20.bin \
    file://LICENCE \
"

LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENCE;md5=a1b1cf31b1b3d185409f31cb9beb25dc"

FILES_${PN} = " /lib/firmware/apalis-tk1-k20.bin "

do_install () {
    install -d ${D}/lib/firmware/
    install -m 0644 ${WORKDIR}/apalis-tk1-k20.bin ${D}/lib/firmware/
}
