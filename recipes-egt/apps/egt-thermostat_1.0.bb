DESCRIPTION = "Microchip EGT Theroststat Demo Application"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;endline=202;md5=3b83ef96387f14655fc854ddc3c6bd57"

PACKAGES = "\
    ${PN} \
    ${PN}-dev \
    ${PN}-dbg \
"
DEPENDS = " libegt sqlite3-native"

SRC_URI = "git://github.com/linux4sam/egt-thermostat.git;protocol=https"

SRCREV = "f1b4390f4b6b3c845b424b654453f49efe690412"

S = "${WORKDIR}/git"

# out-of-tree building doesn't appear to work for this package.
B = "${S}"

inherit pkgconfig autotools gettext

do_configure_prepend() {
	( cd ${S};
	${S}/autogen.sh; cd -)
}

do_install_append() {
	( cd ${S} && sqlite3 thermostat.db < thermostat.sql; cd - )
	install -m 0755 -D ${S}/thermostat.db ${D}${datadir}/egt/thermostat/thermostat.db
}

FILES_${PN} += " \
    ${datadir}/egt/* \
"
