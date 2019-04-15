FILESEXTRAPATHS_prepend_tegra124m := "${THISDIR}/files:"

PACKAGE_ARCH_tegra124m = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE_tegra124m = "(apalis-tk1-mainline)"

SRC_URI_append_tegra124m = "\
    file://0001-compositor-drm-Add-new-gbm-struct-to-allow-for-a-sep.patch \
    file://0002-compositor-drm-Add-support-for-Tegra-Jetson-TK1.patch \
    file://0003-Add-glFinish-to-DRM-backend-to-avoid-tearing.patch \
    file://0004-use-name-based-detection-of-display-and-render-nodes.patch \
"
