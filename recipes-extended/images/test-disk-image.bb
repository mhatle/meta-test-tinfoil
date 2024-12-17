DESCRIPTION = "Target Disk image for compositing target images rootfs and other binaries to a single wic image"
LICENSE ?= "MIT"
PACKAGES = ""

# Note this recipe is NOT compatible with populate_sdk!  For an SDK, use one of the regulr image recipes.
inherit image

# wic may not be enabled, so we force what we need.
IMAGE_FSTYPES = "wic wic.xz wic.bmap"

# Reset the IMGCLASSES
IMGCLASSES  = "rootfs_${IMAGE_PKGTYPE} image_types ${IMAGE_CLASSES}"
IMGCLASSES += "image_types_wic"

# Clear everything else
INSTALL_IMAGE = ""
PACKAGE_IMAGE = ""
TOOLCHAIN_TARGET_TASK = ""
TOOLCHAIN_TARGET_TASK_ATTEMPTONLY = ""
POPULATE_SDK_POST_TARGET_COMMAND = ""

TEST_IMAGE_ROOTFS = "${DEPLOY_DIR_IMAGE}/core-image-minimal${IMAGE_MACHINE_SUFFIX}${IMAGE_NAME_SUFFIX}.tar.bz2"
TEST_IMAGE_ROOTFS_DIR = "${WORKDIR}/rootfs-core-image-minimal"
TEST_IMAGE_TWO_ROOTFS = "${DEPLOY_DIR_IMAGE}/core-image-full-cmdline${IMAGE_MACHINE_SUFFIX}${IMAGE_NAME_SUFFIX}.tar.bz2"
TEST_IMAGE_TWO_ROOTFS_DIR = "${WORKDIR}/rootfs-core-image-full-cmdline"

WICVARS:append = "\
    WORKDIR \
    TEST_IMAGE_ROOTFS_DIR \
    TEST_IMAGE_TWO_ROOTFS_DIR \
    "

DEPENDS += " \
    core-image-minimal \
    core-image-full-cmdline \
    "

WKS_FILES = "test-disk-multi-rootfs.wks"

do_rootfs[depends] += " \
    core-image-minimal:do_build \
    core-image-full-cmdline:do_build \
    "

fakeroot do_rootfs() {
    (
     mkdir -p ${TEST_IMAGE_ROOTFS_DIR}
     cd ${TEST_IMAGE_ROOTFS_DIR}
     tar xvpfSj ${TEST_IMAGE_ROOTFS}
    )

    (
     mkdir -p ${TEST_IMAGE_TWO_ROOTFS_DIR}
     cd ${TEST_IMAGE_TWO_ROOTFS_DIR}
     tar xvpfSj ${TEST_IMAGE_TWO_ROOTFS}
    )
}

do_rootfs[cleandirs] += "${TEST_IMAGE_ROOTFS_DIR} ${TEST_IMAGE_TWO_ROOTFS_DIR}"
