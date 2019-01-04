SUMMARY = "NVIDIAS tegrastats"
DESCRIPTION = "NVIDIAS tegrastats gives information about cpu use for TK1"
LICENSE = "CLOSED"
PR = "r3"

SRC_URI = "file://tegrastats"

S = "${WORKDIR}"

# Inhibit warnings about files being stripped.
# Inhibit warnings about missing DEPENDS, Files are provided in binary form"
INSANE_SKIP_${PN} = "already-stripped build-deps file-rdeps ldflags"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/tegrastats* ${D}${bindir}/
}

FILES_${PN} = "${bindir}/tegrastats"
PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "tegra124"
