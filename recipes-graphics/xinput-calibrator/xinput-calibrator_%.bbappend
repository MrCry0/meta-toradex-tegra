# Prevent running xinput_calibrator twice
do_install_append() {
     # Remove the xinput_calibrator.desktop from /etc/xdg/autostart
     rm -f ${D}${sysconfdir}/xdg/autostart/xinput_calibrator.desktop
}

