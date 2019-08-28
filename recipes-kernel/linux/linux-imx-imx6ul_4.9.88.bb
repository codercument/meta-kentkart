# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2017-2018 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc



SRCBRANCH = "imx_4.9.88_2.0.0_ga"

#KERNEL_SRC ?= "git://${BSPDIR}/local_repos/linux-imx/git;protocol=file"
#KERNEL_SRC ?= "git://${BSPDIR}/build_kentkart/tmp/work/imx6dlkenttablet-poky-linux-gnueabi/linux-imx/4.9.88-r0/git;protocol=file"
KERNEL_SRC = "git://source.codeaurora.org/external/imx/linux-imx.git;protocol=https"
#SRCBRANCH = "imx6dl_Kenttablet_yocto_v1"
LOCALVERSION = "-${SRCBRANCH}"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"
#SRCREV = "5e23f9d6114784d77fd4ed5848953356c3575532"
#SRCREV = "6507266728a7806d850ce22ec923d90cf4aa4318"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"




DEPENDS += "lzop-native bc-native"

DEFAULT_PREFERENCE = "1"

#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

#SRC_URI_append     = " file://0001-TEE-249-PL310-unlock-ways-during-initialization.patch"

DO_CONFIG_V7_COPY = "no"
DO_CONFIG_V7_COPY_mx6 = "yes"
DO_CONFIG_V7_COPY_mx7 = "yes"
DO_CONFIG_V7_COPY_mx8 = "no"

addtask copy_defconfig after do_unpack before do_preconfigure
do_copy_defconfig () {
    install -d ${B}
    if [ ${DO_CONFIG_V7_COPY} = "yes" ]; then
        # copy latest imx_v7_defconfig to use for mx6, mx6ul and mx7
        mkdir -p ${B}
        cp ${S}/arch/arm/configs/imx_v7_defconfig ${B}/.config
        cp ${S}/arch/arm/configs/imx_v7_defconfig ${B}/../defconfig
    else
        # copy latest defconfig to use for mx8
        mkdir -p ${B}
        cp ${S}/arch/arm64/configs/defconfig ${B}/.config
        cp ${S}/arch/arm64/configs/defconfig ${B}/../defconfig
    fi
}

COMPATIBLE_MACHINE = "(imx6ulval6)"
EXTRA_OEMAKE_append_mx6 = " ARCH=arm"
EXTRA_OEMAKE_append_mx7 = " ARCH=arm"
EXTRA_OEMAKE_append_mx8 = " ARCH=arm64"
