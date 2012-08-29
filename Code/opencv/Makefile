# $Id$

CXXFLAGS = `pkg-config --cflags opencv`
LIBS_STATIC = -Lstatic-libs/
INCLUDES = `pkg-config --libs opencv`

#BINARY_SVF   = openCVtest
BINARY_SVF   = saveMovements

all: $(BINARY_SVF) 

$(BINARY_SVF): $(BINARY_SVF).c $(OBJECTS_SVF) Makefile
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o $(BINARY_SVF) $(BINARY_SVF).c $(OBJECTS_SVF)

%.o: %.cc %.h Makefile
	$(CXX) $(CXXFLAGS) -c -o $@ $<

clean:
	rm -f $(BINARY_SVF) 
	

distclean: clean


help:
	@echo "baut so Sachen"


.PHONY: clean help
