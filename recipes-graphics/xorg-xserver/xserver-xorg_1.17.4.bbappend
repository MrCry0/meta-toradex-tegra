# always latest and greatest.
PE_tegra124 = "99"

PACKAGE_ARCH_tegra124 = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(apalis-tk1|jetson-tk1|jetson-tk1-l4t)"

DEPENDS_append = " libxfont"

# provided by xf86-input-evdev_2.10.0
do_install_append () {
    rm -f ${D}/usr/share/X11/xorg.conf.d/10-evdev.conf
}

#| checking for PIXMAN... yes
#| checking for SYSTEMD_DAEMON... no
#| configure: error: systemd support requested but no library has been found
#| ERROR: Function failed: do_configure (log file is located at .../xserver-xorg/2_1.17.2-r0/temp/log.do_configure.2158)

PACKAGECONFIG ?= "dri2 udev ${XORG_CRYPTO} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'dri glx', '', d)} \
                   ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "xwayland", "", d)} \
"

# The NVidia driver needs this enabled.
PACKAGECONFIG_append = " xinerama "
