CFLAGS  += -Wall -g
CC      ?= gcc

all: hciattach_quectel

OBJS := hciattach.o hciattach_rtk.o hciattach_h4.o rtb_fwc.o

hciattach_quectel: $(OBJS)
	$(CC) -o $@ $(OBJS) $(LDFLAGS)

%.o: %.c
	$(CC) -c $< -o $@ $(CFLAGS)

clean:
	rm -f $(OBJS) hciattach_quectel cscope.files cscope.in.out cscope.po.out cscope.out tags

.PHONY: clean