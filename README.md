Reproducer test case for a multiple rootfs partition image

Using the scarthgap branch of the Yocto Project:

. ./oe-init-build-env build-multi-rootfs-partition
bitbake-layers add-layer <this layer>
MACHINE=qemuarm64 bitbake test-disk-image

[this will succeed]

edit your local.conf file, and add: INHERIT += "rm_work"

MACHINE=qemuarm64 bitbake test-disk-image -c cleanall
MACHINE=qemuarm64 bitbake test-disk-image

results in:

  ERROR: test-disk-image-1.0-r0 do_rootfs: The file /usr/lib/aarch64-poky-linux/13.3.0/libgcov.a is installed by both libgcc and libgcc-initial, aborting
  ERROR: Logfile of failure stored in: /build-poky/tmp/work/qemuarm64-poky-linux/test-disk-image/1.0/temp/log.do_rootfs.3553513
  ERROR: Task (/build-poky/meta-test/recipes-extended/images/test-disk-image.bb:do_rootfs) failed with exit code '1'
