// Copyright 2011 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.hughes.android.dictionary.engine;

import com.hughes.util.IndexedObject;
import com.hughes.util.StringUtil;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class AbstractEntry extends IndexedObject {

    public final EntrySource entrySource;

    protected AbstractEntry(EntrySource entrySource) {
        super(-1);
        this.entrySource = entrySource;
    }

    public AbstractEntry(Dictionary dictionary, DataInput raf, final int index)
            throws IOException {
        super(index);
        if (dictionary.dictFileVersion >= 1) {
            final int entrySouceIdx = dictionary.dictFileVersion >= 7 ? StringUtil.readVarInt(raf) : raf.readShort();
            this.entrySource = dictionary.sources.get(entrySouceIdx);
        } else {
            this.entrySource = null;
        }
    }

    public void write(DataOutput raf) throws IOException {
        StringUtil.writeVarInt(raf, entrySource.index());
    }

    public abstract void addToDictionary(final Dictionary dictionary);

    public abstract RowBase CreateRow(int rowIndex, Index dictionaryIndex);

}
