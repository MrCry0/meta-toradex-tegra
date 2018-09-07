FILESEXTRAPATHS_prepend := "${THISDIR}:"

# Unfortunately perf in such obsolete kernels is not yet as nicely separated
# therefore just copy over some more stuff for it to compile again
PERF_SRC_LEGACY_TEGRAS = "Makefile \
                          arch/arm/include \
                          include \
                          lib \
                          tools \
"
PERF_SRC_apalis-tk1 = "${PERF_SRC_LEGACY_TEGRAS}"
PERF_SRC_apalis-t30 = "${PERF_SRC_LEGACY_TEGRAS}"
PERF_SRC_colibri-t30 = "${PERF_SRC_LEGACY_TEGRAS}"
