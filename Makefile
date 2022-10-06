ifeq ($(OS), Windows_NT)
	EXECUTABLE=calc.bat
else
	EXECUTABLE=calc
endif

help: ## Display this help screen
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n"} /^[a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)
install: ## install application
	$(MAKE) native && rm -f /usr/local/bin/calc && ln -s $(shell pwd)/target/calc /usr/local/bin/calc
.PHONY: install
build: ## build application
	./mvnw clean package appassembler:assemble
.PHONY: build
native:
	./mvnw clean verify -DskipTest
.PHONY: native
run:
	./target/appassembler/bin/$(EXECUTABLE) $(filter-out $@,$(MAKECMDGOALS))
.PHONY:
%:
	@: