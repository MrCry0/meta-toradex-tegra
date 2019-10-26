# Unfortunately perf in such obsolete kernels is not yet as nicely separated
# therefore just copy over some more stuff for it to compile again
PERF_SRC_LEGACY_TEGRAS = "Makefile \
                          arch/arm/include \
                          include \
                          lib \
                          tools \
"
PERF_SRC_tegra124 = "${PERF_SRC_LEGACY_TEGRAS}"

do_configure_prepend_tegra124 () {
    sed -i 's$I/usr/include/slang$I=/usr/include/slang$' tools/perf/Makefile
}

# scripting is confiugred to use python3, however this requires a build
# against a kernel of version 5.x and later or a patch to the kernel.
# see also https://git.yoctoproject.org/cgit/cgit.cgi/poky/commit/meta/recipes-kernel/perf?id=92469aad50b623afa423c19d82ed2e3c667c5e6a
PACKAGECONFIG_remove_tegra124 = "scripting"
