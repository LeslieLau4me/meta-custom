# 通用自定义Yocto层
适配：i.MX9系列 米尔硬件
功能：静态IP网络配置、蓝牙WiFi、Redis数据库、定制系统镜像

## 接入步骤
1. 进入工程sources目录
cd 工程路径/sources
2. 克隆本层
git clone 仓库地址 meta-custom
3. 工程配置文件添加层
打开conf/bblayers.conf
在BBLAYERS变量末尾追加：
${BSPDIR}/sources/meta-custom \
4. 配置环境编译镜像即可使用 bitbake myir-image-emmc-nqt 