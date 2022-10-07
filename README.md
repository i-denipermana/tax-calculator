# Tax Calculator (tcalc) 
[![Release](https://github.com/i-denipermana/tax-calculator/actions/workflows/release.yaml/badge.svg)](https://github.com/i-denipermana/tax-calculator/actions/workflows/release.yaml) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=i-denipermana_tax-calculator&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=i-denipermana_tax-calculator) ![GitHub release (latest by date)](https://img.shields.io/github/v/release/i-denipermana/tax-calculator)


## Installation

### Download

Download from github [releases page](https://github.com/i-denipermana/tax-calculator/releases). Extract the zip/tar file and run command
```shell
$ chmod +x ./tcalc-${VERSION}-${OS}-x86_64/bin/tcalc 
$ ./tcalc-${VERSION}-${OS}-x86_64/bin/tcalc GST 123 
```
if you want to install globally on macOS or linux you can move the binary to `/usr/local/bin`
```bash
$ mv ./tcalc-${VERSION}-${OS}-x86_64/bin/tcalc /usr/local/bin
$ tcalc
Usage: tcalc [-hV] <taxType> <customerId> <file>
      <taxType>      Tax Type (GST, PAYROLL, COMPANY_TAX, LAND_TAX,
                       CAPITOL_GAIN)
      <customerId>   Your customer id
      <file>         Path to filename
  -h, --help         Show this help message and exit.
  -V, --version      Print version information and exit.

```
### Build native
Before build locally there are several things that we need to install.
#### Requirements
- Java 11 or newer
- GraalVM for building native image

For GraalVM installation you can refer to the [documentation](https://www.graalvm.org/22.2/docs/getting-started/). After install GraalVM install native image by running 
```shell
$ gu install native-image
```

After installation now we can run the packager
```shell
$ ./mvnw clean install
```

### Build with assembler plugin
you can build this package with appassembler maven plugin with command below.
```shell
$ ./mvnw clean package appassembler:assemble
```
After the build finish you can run it 
```shell
$ ./target/appassembler/bin/tcalc

Missing required parameters: '<taxType>', '<customerId>', '<file>'
Usage: tcalc [-hV] <taxType> <customerId> <file>
      <taxType>      Tax Type (GST, PAYROLL, COMPANY_TAX, LAND_TAX,
                       CAPITOL_GAIN)
      <customerId>   Your customer id
      <file>         Path to filename
  -h, --help         Show this help message and exit.
  -V, --version      Print version information and exit.

```
## Usage
```shell
$ tcalc
Usage: calc [-hV] <taxType> <customerId> <file>
      <taxType>      Tax Type (GST, PAYROLL, COMPANY_TAX, LAND_TAX,
                       CAPITOL_GAIN)
      <customerId>   Your customer id
      <file>         Path to filename
  -h, --help         Show this help message and exit.
  -V, --version      Print version information and exit.
```
## Contributing
