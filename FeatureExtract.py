#!/usr/bin/env python
# coding: utf-8

# In[26]:


import librosa
import sys

def loadAudio(path):
    audio_path = path
    x , sr = librosa.load(audio_path)
    return [x,sr]

def getZeroCrossing(audio):
    x = audio[0]
    zero_crossings = librosa.zero_crossings(x[:-1], pad=False)
    return sum(zero_crossings)/len(zero_crossings)

def getSC(audio):
    x, sr = audio
    spectral_centroids = librosa.feature.spectral_centroid(x, sr=sr)
    return spectral_centroids

def getMFCC(audio):
    x, sr = audio
    mfccs = librosa.feature.mfcc(x, sr=sr)
    myMfcc = list()
    for i in range(0,len(mfccs)):
        mSum=0
        for j in range(0,len(mfccs[0])):
            mSum=mSum+mfccs[i][j]
        myMfcc.append(mSum/len(mfccs[0]))
    return myMfcc

audio = loadAudio(sys.argv[1])
print("ZeroCrossing : !"+str(getZeroCrossing(audio))+"?")
#print("Spectral Centroids : ")
#getSC(audio).shape
print("MFCC : ",end='')
mfcc=getMFCC(audio)
for i in mfcc:
    print(end='!')
    print(i,end='?')

print("#")
