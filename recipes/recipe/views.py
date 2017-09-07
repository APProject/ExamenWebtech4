from django.shortcuts import render
from django.http import HttpResponse
from django.template import RequestContext, loader
from django.views import generic
from urllib import quote  
from urllib import urlopen
import json
from pymongo import Connection
from pymongo.errors import ConnectionFailure

r = Connection(host="localhost", port=27017)

def index(request):
	return render(request, 'recipe/index.html', {})
    #return render(request, 'jokes/index.html')

def joke(request, name, calorien, ingredienten, tijd):
	url = 'http://api.icndb.com/jokes/random?firstName=' + quote(first_name) + '&lastName=' + quote(last_name)
	response = urlopen(url)
	obj = response.read().decode("utf8")
	data = json.loads(obj)
	
	if r.exists('joke:' + str(data['value']['id'])) == 0:
		r.set('joke:' + str(data['value']['id']), data['value']['joke'])
	
	return render(request, 'recipe/index.html', {'recipe': data['value']['joke'], 'first_name': first_name, 'last_name': last_name})