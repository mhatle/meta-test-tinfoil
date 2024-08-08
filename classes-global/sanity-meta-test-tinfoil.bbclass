addhandler tinfoil_bbappend_distrocheck
tinfoil_bbappend_distrocheck[eventmask] = "bb.event.SanityCheck"
python tinfoil_bbappend_distrocheck() {
    skip_check = e.data.getVar('SKIP_META_TEST_TINFOIL_SANITY_CHECK') == "1"
    if e.data.getVar('WITH_TINFOIL') != '1' and not skip_check:
        bb.warn("You have included the meta-test-tinfoil layer, but \
it has not been enabled using WITH_TINFOIL in your configuration.")
}
