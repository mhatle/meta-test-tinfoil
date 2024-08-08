SUMMARY = "Test Package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

INHIBIT_DEFAULT_DEPS = "1"

inherit native

do_fetch[noexec] = "1"
do_unpack[noexec] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

SYSROOT_DIRS_NATIVE += "${STAGING_DIR_NATIVE}/test-cases"

do_install() {
    mkdir -p ${D}${STAGING_DIR_NATIVE}/test-cases
    echo "Test file!" > ${D}${STAGING_DIR_NATIVE}/test-cases/test-output
}

python() {
    if d.getVar('WITH_TINFOIL') != '1':
        raise bb.parse.SkipRecipe("This package requires WITH_TINFOIL, which is not set to '1'.  WITH_TINFOIL set to '%s'." % d.getVar('WITH_TINFOIL'))
}
