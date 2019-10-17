# always latest and greatest.

require recipes-graphics/xorg-xserver/xserver-xorg.inc

COMPATIBLE_MACHINE = "(tegra124)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PE = "99"

DEPENDS_append = " libxfont"

SRC_URI[md5sum] = "1509a9daae713895e7f5bcba8bcc05b2"
SRC_URI[sha256sum] = "0c4b45c116a812a996eb432d8508cf26c2ec8c3916ff2a50781796882f8d6457"

SRC_URI += " \
    file://0001-sysmacros.h-follow-changed-include-files.patch \
    file://0001-compiler.h-Do-not-include-sys-io.h-on-ARM-with-glibc.patch \
"

# These extensions are now integrated into the server, so declare the migration
# path for in-place upgrades.

RREPLACES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RPROVIDES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RCONFLICTS_${PN} = "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "

# The NVidia driver needs xinerama enabled.

# override setting from xserver-xorg.inc which only applies to version 1.18
# remove systemd:
#| checking for PIXMAN... yes
#| checking for SYSTEMD_DAEMON... no
#| configure: error: systemd support requested but no library has been found
#| ERROR: Function failed: do_configure (log file is located at /build/krm/oe-core_V2.6.2/build/tmp-glibc/work/armv7at2hf-neon-angstrom-linux-gnueabi/xserver-xorg/2_1.17.2-r0/temp/log.do_configure.2158)

# provide dga to prevent runtime error
# X: symbol lookup error: /usr/lib/xorg/modules/drivers/nvidia_drv.so: undefined symbol: DGAInit

PACKAGECONFIG ?= "dga dri2 udev xinerama ${XORG_CRYPTO} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'dri', '', d)} \
                   ${@bb.utils.contains("DISTRO_FEATURES", "opengl wayland", "xwayland", "", d)} \
"

# provided by xf86-input-evdev_2.10.0
do_install_append () {
    rm -f ${D}/usr/share/X11/xorg.conf.d/10-evdev.conf
}
