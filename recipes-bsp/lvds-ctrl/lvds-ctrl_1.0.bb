SUMMARY = "Scripts to enable the LVDS converter on Apalis T30"
LICENSE = "PD"

SRC_URI = " \
    file://lvds-dual-channel.sh \
    file://lvds-single-channel.sh \
    file://lvds-single-channel-24bit-mode2.sh \
    file://COPYING \
"

LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=1c3a7fb45253c11c74434676d84fe7dd"

do_install () {
    install -d ${D}/${bindir}
    install -m 0755 ${WORKDIR}/*.sh ${D}/${bindir}
}

pkg_postinst_${PN}() {
    mkdir -p ${sysconfdir}/xdg/lxsession/LXDE
    echo "${bindir}/lvds-dual-channel.sh" >> ${sysconfdir}/xdg/lxsession/LXDE/autostart
}

pkg_postrm_${PN}() {
    sed -i lvds-dual-channel.sh/d ${sysconfdir}/xdg/lxsession/LXDE/autostart || true
}
