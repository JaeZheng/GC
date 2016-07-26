#!/usr/bin/python3
#coding:utf-8

import socket
import urllib.parse
from DNS_Cache import *
from NetworkHandler import NetworkHandler
import threading

socket.setdefaulttimeout(60)

class DNSResolver:
    """ Class to help resolve url's IP address """

    DNS_cache = DNSCache()

    def __init__(self):
        self._url = ""
        self._url_suffix = ""
        self._host = ""
        self._ip = ""
        self._buffer = collections.OrderedDict()
        self._buffer_size = 200
        self._resolved_url_packet_size = 10

    # set url
    def set_url(self, url):
        self._url = url

    # get domain name and url's suffix
    def resolve_host(self, url):
        self.set_url(url)
        # remove protocol
        protocol, rest = urllib.parse.splittype(self._url)
        # split domain name and url's suffix
        # TODO: need to check whether this method is sufficient to split those
        #       complicated url into host and suffix
        self._host, self._url_suffix = urllib.parse.splithost(rest)

    # get ip address
    def resolve_ip(self):
        url = "www.baidu.com"
        self.resolve_host(url)
        if DNSResolver.DNS_cache.query_cache(self._host, self._ip):
            self._ip = DNS_cache.wanted_ip(self._host)
        else:
            try:
                self._ip = socket.gethostbyname(self._host)
                DNS_cache.add_to_cache(self._host, self._ip)
            except socket.gaierror:
                self._ip = ""
            except Exception as e:
                self._ip = ""
                # print("cannot attach the remote server")
        self._buffer[self._url] = self.get_resolved_url()

    # return primitive url
    def get_url(self):
        return self._url

    # return resolved url's suffix
    def get_url_suffix(self):
        return self._url_suffix

    # return resolved domain name
    def get_host(self):
        return self._host

    # return resolved domain name
    def get_ip(self):
        return self._ip

    # return resolved url
    def get_resolved_url(self):
        if self._ip == "":
            return None
        else:
            return self._ip + self._url_suffix

    # accept page_fetcher's request and response
    def get_resolved_url_packet(self):
        tmp = []
        #when _buffer is pretty small, get some links
        if (len(self._buffer) <= 5 * self._resolved_url_packet_size):
            for _ in range(self._buffer_size):
                self.resolve_ip()
            for _ in range(self._resolved_url_packet_size):
                temp.append(self._buffer.popitem(last=False))
        else:
            for _ in range(self._resolved_url_packet_size):
                temp.append(self._buffer.popitem(last=False))
        return dict(temp)

test = DNSResolver()
test.resolve_ip()
