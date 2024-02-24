import Foundation
#if canImport(BSDiffC)
import BSDiffC
#endif

public enum DiffError : Error {
    case io(msg: String?)
    case alloc(msg: String?)
    case bz2(msg: String?)
    case unknown(code: Int, msg: String?)
}

public enum PatchError : Error {
    case io(msg: String?)
    case alloc(msg: String?)
    case bz2(msg: String?)
    case corruptPatch(msg: String?)
    case unknown(code: Int, msg: String?)
}


public func diff(old: String, new: String, patch: String) throws {
    var msgOut: UnsafeMutablePointer<CChar>? = nil
    let result = bsdiff(old, new, patch, &msgOut)
    if result != BSDIFF_SUCCESS {
        let msg = msgOut.map { String(cString: $0) }
        free(msgOut)
        switch result {
        case BSDIFF_ERR_IO:
            throw DiffError.io(msg: msg)
        case BSDIFF_ERR_ALLOC:
            throw DiffError.alloc(msg: msg)
        case BSDIFF_ERR_BZ2:
            throw DiffError.bz2(msg: msg)
        default:
            throw DiffError.unknown(code: Int(result), msg: msg)
        }
    }
}

public func patch(old: String, new: String, patch: String) throws {
    var msgOut: UnsafeMutablePointer<CChar>? = nil
    let result = bspatch(old, new, patch, &msgOut)
    if result != BSDIFF_SUCCESS {
        let msg = msgOut.map { String(cString: $0) }
        free(msgOut)
        switch result {
        case BSDIFF_ERR_IO:
            throw PatchError.io(msg: msg)
        case BSDIFF_ERR_ALLOC:
            throw PatchError.alloc(msg: msg)
        case BSDIFF_ERR_BZ2:
            throw PatchError.bz2(msg: msg)
        case BSDIFF_ERR_CORRUPT_PATCH:
            throw PatchError.corruptPatch(msg: msg)
        default:
            throw PatchError.unknown(code: Int(result), msg: msg)
        }
    }
}
