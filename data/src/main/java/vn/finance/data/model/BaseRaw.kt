package vn.finance.data.model

import vn.finance.domain.model.BaseModel

abstract class BaseRaw {
    abstract fun raw2Model(): BaseModel?
}
